package spharos.settle.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomJobParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if( parameters.getString("requestDate") == null) {
            throw new JobParametersInvalidException("inputFile parameter is missing");
        }
    }
}
