package com.irdeto.jumpstart.manager;

import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlIPList;

public interface JumpstartControlManager {

	public void registerContent(ControlBatch batch) throws Exception;

	public void ipList(ControlIPList ipList) throws Exception;

}
