package com.example.workflow.data.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskResponse {
    private Boolean isSuccess;
    private List<TaskList> taskList;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TaskList{
        private String taskId;
        private String taskName;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String taskDate;
        private String userId;
        private Boolean isAssign;
        private Assign assign;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Assign{
            private List<String> userIds;
        }

    }
}
