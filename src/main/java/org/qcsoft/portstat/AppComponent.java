/*
 * Copyright 2019-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qcsoft.portstat;

import java.util.List;

import org.onosproject.net.Device;
import org.onosproject.net.Port;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;

    @Activate
    protected void activate() {
        log.info("Started");
        Iterable<Device> devices = deviceService.getDevices();
        for (Device d : devices) {
        	log.info("#### [viswa] Device id " + d.id().toString());

            List<Port> ports = deviceService.getPorts(d.id());
            for(Port port : ports) {
                log.info("Getting info for port [" + port.number() + "]");
                PortStatistics portstat = deviceService.getStatisticsForPort(d.id(), port.number());
                PortStatistics portdeltastat = deviceService.getDeltaStatisticsForPort(d.id(), port.number());
                if(portstat != null)
                    log.info("portstat bytes recieved " + portstat.bytesReceived());
                else
                    log.info("Unable to read portStats");

                if(portdeltastat != null)
                    log.info("portdeltastat bytes recieved " + portdeltastat.bytesReceived());
                else
                    log.info("Unable to read portDeltaStats");
            }

            
//        	List<PortStatistics> portStatisticsList = deviceService.getPortDeltaStatistics(d.id());
//            for (PortStatistics portStats : portStatisticsList) {
//                try {
//                    int port = portStats.port();
//                    log.info("#### Creating object for " + port);
//                    portStatsReaderTask task = new portStatsReaderTask();
//                    task.setDelay(3);
//                    task.setExit(false);
//                    task.setLog(log);
//                    task.setPort(port);
//                    task.setDeviceService(deviceService);
//                    task.setDevice(d);
//                    map.put(port, task);
//                    task.schedule();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Deactivate
    protected void deactivate() {
        log.info("Stopped");
    }

}
