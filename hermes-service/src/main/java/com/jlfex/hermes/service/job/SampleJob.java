package com.jlfex.hermes.service.job;

import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;

/**
 * @author ultrafrog
 * @version 1.0, 2014-02-14
 * @since 1.0
 */
@Component("sampleJob")
public class SampleJob extends Job {

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.job.Job#run()
	 */
	@Override
	public Result run() {
		Logger.info("This is a sample job.");
		return new Result(true, true, "样例任务执行成功");
	}
}
