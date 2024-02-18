package com.hightoon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hightoon.azure.AzureGPTApiRequest;
import com.hightoon.dto.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GPTController {

    private final AzureGPTApiRequest azureGPTApiRequest;

    //이상적인사람 - 방법론을 논한다
    @PostMapping("/ideal")
    public Object ideal(@RequestBody Request request) {
        return azureGPTApiRequest.sendRequest(request , 1);
    }

    //현실적인사람 - 부정적 & 노력에대한 비판
    @PostMapping("/realistic")
    public Object realistic(@RequestBody Request request) {
        return azureGPTApiRequest.sendRequest(request , 2);
    }

    //감각적인사람 - 긍정적
    @PostMapping("/sensuous")
    public Object sensuous(@RequestBody Request request) {
        return azureGPTApiRequest.sendRequest(request , 3);
    }

    //대화 이어가기
    @PostMapping("/continue")
    public Object keep(@RequestBody Request request) {
        return azureGPTApiRequest.sendRequest(request , 4);
    }

}
