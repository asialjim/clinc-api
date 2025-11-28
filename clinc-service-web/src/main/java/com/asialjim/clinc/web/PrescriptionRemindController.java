/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.clinc.web;

import com.asialjim.clinc.cons.ClincRoleCode;
import com.asialjim.clinc.service.PrescriptionRemindService;
import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.asialjim.clinc.vo.PrescriptionRemindVo;
import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.commons.security.RoleCode;
import com.asialjim.microapplet.commons.security.RoleNeed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户提醒器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/prescription/reminder")
public class PrescriptionRemindController {
    private final PrescriptionRemindService prescriptionRemindService;

    /**
     * 获取当前用户具有查询权限的就诊提醒记录
     *
     * @param page  {@link Long 分页参数：页码}
     * @param size  {@link Long 分页参数：页大小}
     * @param name  {@link String 姓名}
     * @param idNo  {@link String 证件号}
     * @param phone {@link String 手机号}
     * @return {@link PageData<PrescriptionRemindVo> }
     * @apiNote 当用户是手机号用户时，只能查询自己的提醒记录，当用户时专业用户时可以查询所有记录或者根据姓名、证件号、手机号查询记录
     * @ignoreResponseBodyAdvice
     * @response {"status": 200,"thr": false,"pageable": false,"code": "0","msg": "ok","data":  [{"lastDate": "yyyy-MM-dd","lastDays": 0,"nextDate": "yyyy-MM-dd","phone": ""}]"errs": [""],"page": 1,"size": 10,"pages": 10,"total": 100}
     * @since 2025/10/24
     */
    @GetMapping("/list")
    @RoleNeed(any = {RoleCode.PHONE_BIT, ClincRoleCode.NURSE_BIT, ClincRoleCode.DOCTOR_BIT})
    public PageData<PrescriptionRemindVo> list(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "5") Long size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String idNo,
            @RequestParam(required = false) String phone) {

        return this.prescriptionRemindService.query(page, size, name, idNo, phone);
    }

    /**
     * 根据记录编号精确查询详情
     *
     * @param id {@link String id}
     * @return {@link PrescriptionRecordVo }
     * @since 2025/11/28
     */
    @GetMapping("/{id}")
    @RoleNeed(any = {RoleCode.PHONE_BIT, ClincRoleCode.NURSE_BIT, ClincRoleCode.DOCTOR_BIT})
    public PrescriptionRecordVo queryById(@PathVariable(name = "id") String id) {
        return this.prescriptionRemindService.queryById(id);
    }
}