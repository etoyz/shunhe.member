package com.sh.cloud.web.member;

import carinfo.bean.CarInfo;
import carinfo.bean.CarSeries;
import carinfo.bean.CarSeriesGroup;
import carinfo.service.CarDataQuery;
import com.sft.member.bean.*;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.member.MemberService;
import com.sft.member.obtain.pay.PayService;
import com.sft.member.obtain.user.PlatUserService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import com.sh.cloud.entity.GetUserListRequest;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("service/archives")
public class ArchivesController {
    @Resource
    UserService shUserService;
    @Resource
    MemberService memberService;
    @Resource
    CarDataQuery CarService;
    @Resource
    PayService payService;
    @Resource
    PlatUserService platUserService;
    @Resource
    LogService logService;

    /**
     * 通过职务获取用户列表
     *
     * @param post 保客主管|保客专员|服务顾问|销售顾问
     * @return 获取到的用户列表
     */
    @RequiresPermissions(value = {"member:archives:more", "member:archives:add"}, logical = Logical.OR)
    @RequestMapping("getUserByPost")
    public List<PlatUser> getUserByPost(@RequestParam String post) {
        PlatUser user = new PlatUser();
        user.post = post;
        return platUserService.getPlatUserListByPost(user);
    }

    /**
     * 获取某客户卡券和积分的统计信息
     *
     * @param userid 客户id
     * @return 各统计信息
     */
    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getUserCost")
    public UserCost getUserCost(@RequestParam String userid) {
        return payService.getUserCost(String.valueOf(userid));
    }

    /**
     * 新增客户档案
     *
     * @param user 客户具体信息
     * @return "成功！"|错误信息
     */
    @RequiresPermissions(value = {"member:archives:add"}, logical = Logical.OR)
    @PostMapping(value = "addArchives")
    public String addUser(@RequestBody User user) {
        String ret = shUserService.addUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        if (ret == null || ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增客户档案 客户名称:" + user.customername
                            + "、车架号:" + user.vehicle.vin
                            + "、车牌号:" + user.vehicle.platenumber
                            + "、手机号:" + user.phone
                            + "、品牌:" + user.vehicle.vehiclelabel
                            + "、车系:" + user.vehicle.vehiclestyle
                            + "、车型:" + user.vehicle.vehiclebrand
                    ));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 通过Excel文件批量导入用户
     *
     * @param file Excel文件
     * @return 导入的详细信息，包括错误提示
     */
    @RequiresPermissions(value = {"member:archives:add"}, logical = Logical.OR)
    @PostMapping(value = "addArchivesBatch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JSONObject addUserBatch(@RequestParam("file") MultipartFile file) {
        JSONObject resp = new JSONObject();
        resp.put("cnt", 0);
        StringBuilder errorMsg = new StringBuilder("错误信息：\n");
        boolean isErrorOccur = false;
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            List<String> fields = new ArrayList<>();
            Row row1 = itr.next();
            Iterator<Cell> cellIterator1 = row1.cellIterator();
            while (cellIterator1.hasNext()) {
                Cell cell = cellIterator1.next();
                fields.add(cell.getStringCellValue());
            }
            List<User> userList = new ArrayList<>();
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                User user = new User();
                user.member = new Member();
                user.vehicle = new Vehicle();
                int i = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (fields.get(i).equals("客户姓名"))
                        user.customername = cell.getStringCellValue();
                    else if (fields.get(i).equals("手机号"))
                        user.phone = String.format("%.0f", cell.getNumericCellValue());
                    else if (fields.get(i).equals("车架号"))
                        user.vehicle.vin = cell.getStringCellValue();
                    else if (fields.get(i).equals("车牌号"))
                        user.vehicle.platenumber = cell.getStringCellValue();
                    else
                        break;
                    i++;
                }
                userList.add(user);
            }

            for (User user : userList) {
                String ret = shUserService.addInputUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
                if (ret == null || ret.equals("")) {
                    logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                            LogUtils.newLogInstance("导入客户档案 客户名称:" + user.customername
                                    + "、车架号:" + user.vehicle.vin
                                    + "、车牌号:" + user.vehicle.platenumber
                                    + "、手机号:" + user.phone
                            ));
                    resp.put("cnt", resp.getInt("cnt") + 1);
                } else {
                    isErrorOccur = true;
                    logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                            LogUtils.newLogInstance("导入客户档案异常：" + ret));
                    errorMsg.append("\t导入客户档案异常：").append(ret).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("导入文件解析异常：" + e.getMessage()));
            errorMsg.append("\t导入文件解析异常：").append(e.getMessage()).append("\n");
            resp.put("errorMsg", errorMsg);
            return resp;
        }
        if (isErrorOccur)
            resp.put("errorMsg", errorMsg.toString());
        return resp;
    }

    /**
     * (已弃用) 通过客户名称查询客户列表
     *
     * @param query 客户名称
     * @return 查询到的客户列表
     */
    @RequiresPermissions(value = {"member:archivesRoot"}, logical = Logical.OR)
    @GetMapping("getArchivesList")
    @Deprecated
    public Map<String, Object> getArchivesList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        User c = new User();
        c.customername = query;
        List<User> data = shUserService.getUserList(c, page, limit);
//        data.addAll(data);
        ret.put("count", shUserService.getUserList(c));
        ret.put("data", data);

        return ret;
    }

    /**
     * 通过查询参数查询客户列表
     *
     * @param request 查询参数（客户名称、车牌号、车架号、会员卡号等）
     * @return 查询到的客户列表
     */
    @RequiresPermissions(value = {"member:memberUseCoupon:useCoupon"}, logical = Logical.OR)
    @RequestMapping("getUserList")
    public Map<String, Object> getUserList(@RequestBody GetUserListRequest request) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<User> data = shUserService.getUserList(request.getUser(), request.getPage(), request.getLimit());
        ret.put("count", shUserService.getUserList(request.getUser()));
        ret.put("data", data);

        return ret;
    }

    /**
     * 获取某客户消费历史（调用的待审核列表的接口，已经审核通过的消费单视为消费历史）
     *
     * @param userid 客户id
     * @return 获取到的消费历史
     */
    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getConsumptionHistories")
    public Map<String, Object> getConsumptionHistories(@RequestParam String userid, @RequestParam int page, @RequestParam int limit) {
        User user = new User();
        user.userId = userid;
        CouponCheck couponCheck = new CouponCheck();
        couponCheck.type = "1";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("data", payService.getUnCheckRecord(user, couponCheck, page, limit, false));
        ret.put("count", payService.getUnCheckRecordCount(user, couponCheck, false));

        return ret;
    }

    /**
     * 删除某客户信息
     *
     * @param userId 客户id
     * @return "删除成功！"|错误信息
     */
    @RequiresPermissions(value = {"member:archives:delete"}, logical = Logical.OR)
    @PostMapping("deleteArchives")
    public String deleteArchives(@RequestParam String userId) {
        User user = new User();
        user.userId = userId;
        user = shUserService.getUser(user);
        String ret = shUserService.deleteUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        if (ret == null || ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除客户档案 客户名称:" + user.customername
                            + "、会员卡号:" + user.memberNumber
                            + "、车架号:" + user.vehicle.vin
                            + "、车牌号:" + user.vehicle.platenumber
                    ));
            return "删除成功！";
        } else
            return ret;
    }

    /**
     * 编辑客户信息
     *
     * @param user 新的客户信息
     * @return 编辑操作后，新的客户信息
     */
    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @PostMapping(value = "editArchives")
    public User editArchives(@RequestBody User user) {
        User c;
        c = shUserService.editUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑客户档案 会员卡号:" + user.memberNumber));
        return c;
    }

    //    TODO: 用到该URL的页面较多，暂不设权限控制
//    @RequiresPermissions(value = {"member:archives"}, logical = Logical.OR)

    /**
     * 获取某客户具体信息
     *
     * @param userId 客户id
     * @return 具体客户信息
     */
    @PostMapping(value = "getUserInfo")
    public User requireUser(@RequestParam String userId) {
        User c = new User();
        c.userId = userId;
        return shUserService.getUser(c);
    }

    /**
     * 获取会员级别列表
     *
     * @return 会员级别列表
     */
    @RequiresPermissions(value = {"member:archives:level"}, logical = Logical.OR)
    @PostMapping(value = "getMemberNameList")
    public List<Member> getMemberNameList() {
        List<Member> ret = memberService.getMemberNameList();
        return ret;
    }

    /**
     * 获取车品牌列表
     *
     * @return 车品牌列表
     */
    @PostMapping(value = "getBrandNameList")
    public List<CarSeriesGroup> getBrandNameList() {

        List<CarSeriesGroup> ret = CarService.getAllCarSeriesGroups("14");

        return ret;
    }

    /**
     * 根据车品牌获取车型列表
     *
     * @param BrandId 车匹配id
     * @return 车系列表
     */
    @PostMapping(value = "getCarSeriesgroupNameList")
    public List<CarSeries> getCarSeriesgroupNameList(@RequestParam String BrandId) {
        List<carinfo.bean.CarSeries> ret = CarService.getAllCarSeries(BrandId);
        return ret;
    }

    /**
     * 根据车系获取车型列表
     *
     * @param GroupId 车系id
     * @return 车系列表
     */
    @PostMapping(value = "getAllCarSeriesNameList")
    public List<CarInfo> getAllCarSeriesNameList(@RequestParam String GroupId) {
        List<carinfo.bean.CarInfo> ret = CarService.getAllCarInfo(GroupId);
        return ret;
    }

    /**
     * 修改会员级别
     *
     * @param userId 会员id
     * @param id     新的会员级别id
     * @return "添加成功"|错误信息
     */
    @RequiresPermissions(value = {"member:archives:level"}, logical = Logical.OR)
    @PostMapping(value = "alertLevel")
    public String alertLevel(@RequestParam String userId, @RequestParam String id) {
        Member m = new Member();
        m.id = id;
        // m.level="11";
        User c = new User();
        c.userId = userId;
        c.member = m;
        String ret = shUserService.alertLevel(PlatUserUtils.getCurrentLoginPlatUser(), c);
        if (ret == null || ret.equals("")) {
            c = shUserService.getUser(c);
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("修改会员级别 客户名称:" + c.customername
                            + "、会员卡号:" + c.memberNumber
                            + "、车架号:" + c.vehicle.vin
                            + "、车牌号:" + c.vehicle.platenumber
                    ));
            return "添加成功";
        } else
            return ret;
    }
}

