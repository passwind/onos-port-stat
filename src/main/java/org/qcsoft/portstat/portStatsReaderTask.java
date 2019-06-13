package org.qcsoft.portstat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.onosproject.net.Device;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.slf4j.Logger;

public class portStatsReaderTask {
  private Device device;
  private DeviceService deviceService;
  private long delay;
  private boolean exitflag;
  private Logger log;
  private int port;
  private Timer timer = new Timer();
  
  
  
  
  public Timer getTimer() {
	return timer;
}

public void setTimer(Timer timer) {
	this.timer = timer;
}

public void setDevice(Device device) {
	this.device = device;
}

public void setDeviceService(DeviceService deviceService) {
	this.deviceService = deviceService;
}

public void setDelay(long delay) {
	this.delay = delay;
}

public void setExit(boolean exitflag) {
	this.exitflag = exitflag;
}

public void setLog(Logger log) {
	this.log = log;
}

public void setPort(int port) {
	this.port = port;
}

class Task extends TimerTask {


    public Device getDevice() {
      return device;
    }
    public DeviceService getDeviceService() {
      return deviceService;
    }
    public long getDelay() {
      return delay;
    }
    public boolean isExit() {
    	return exitflag;
    }
    public int getPort() {
    	return port;
    }


    @Override
    public void run() {
      while (!isExit()) {
        log.info("####### Into run() ");
        List<PortStatistics> portStatisticsList = getDeviceService().getPortDeltaStatistics(getDevice().id());
        for (PortStatistics portStats : portStatisticsList) {
          log.info("########## port is " + portStats.portNumber().toLong());
          if (portStats.portNumber().toLong() == getPort()) {
            log.info("Port " + port + "has received " + portStats.bytesReceived() + "bytes");
            try {
            	 Thread.sleep((getDelay() * 1000));
                 break;
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  public void schedule() {
    this.getTimer().schedule(new Task(), 0, 1000);
  }
}