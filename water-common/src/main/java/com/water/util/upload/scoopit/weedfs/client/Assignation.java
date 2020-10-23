/*
 * (C) Copyright 2013 Scoop IT SAS (http://scoop.it/) and others.
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
 * 
 * Contributors:
 *     Philippe GASSMANN
 *     Jean-Baptiste BELLET
 */
package com.water.util.upload.scoopit.weedfs.client;

import com.water.util.upload.scoopit.weedfs.client.net.AssignResult;


public class Assignation {

    private WeedFSFile weedFSFile;

    private Location location;

    int versionCount;

    public Assignation(AssignResult result) {
        this.weedFSFile = result.getWeedFSFile();
        this.location = result.getLocation();
        this.versionCount = result.getCount();
    }

    public Assignation() {
    }
    
    

    public WeedFSFile getWeedFSFile() {
		return weedFSFile;
	}

	public void setWeedFSFile(WeedFSFile weedFSFile) {
		this.weedFSFile = weedFSFile;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getVersionCount() {
		return versionCount;
	}

	public void setVersionCount(int versionCount) {
		this.versionCount = versionCount;
	}

	@Override
    public String toString() {
        return "AssignedWeedFSFile [weedFSFile=" + weedFSFile + ", location=" + location + ", versionCount=" + versionCount + "]";
    }

}
