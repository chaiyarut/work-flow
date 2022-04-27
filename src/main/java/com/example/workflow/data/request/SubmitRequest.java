package com.example.workflow.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitRequest {
    private String userId;
    private String taskId;
    private Assign assign;
    private String description;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Assign{
        private List<String> userIds;
    }
}
