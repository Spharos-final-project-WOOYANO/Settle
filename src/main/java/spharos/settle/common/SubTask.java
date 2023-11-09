package spharos.settle.common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {
    private String clientEmail; //사업자 이메일
    private String subTaskName;
    private String taskType; // "payment", "client"
    private String status; // "success", "fail", "ready"
}
