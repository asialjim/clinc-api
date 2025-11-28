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

package com.asialjim.clinc.api;

import com.asialjim.clinc.vo.PrescriptionRecordVo;
import com.asialjim.clinc.vo.QueryLastPrescriptionRecordReq;
import com.asialjim.microapplet.common.page.PageData;
import org.springframework.web.bind.annotation.*;

/**
 * 用药记录API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface PrescriptionRecordApi {
    String path = "/prescription/record";

    @PostMapping("/last")
    PageData<PrescriptionRecordVo> queryLastRecord(@RequestParam(required = false, defaultValue = "1") Long page,
                                                   @RequestParam(required = false, defaultValue = "5") Long size,
                                                   @RequestBody QueryLastPrescriptionRecordReq req);

    @GetMapping("/{id}")
    PrescriptionRecordVo queryById(@PathVariable(name = "id") String id);
}