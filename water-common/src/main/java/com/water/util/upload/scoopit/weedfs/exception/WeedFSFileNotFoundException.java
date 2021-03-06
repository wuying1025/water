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
package com.water.util.upload.scoopit.weedfs.exception;

import com.water.util.upload.scoopit.weedfs.client.Location;
import com.water.util.upload.scoopit.weedfs.client.WeedFSFile;

public class WeedFSFileNotFoundException extends WeedFSException {

    private static final long serialVersionUID = 1L;

    public WeedFSFileNotFoundException(WeedFSFile file, Location location) {
        super(file.fid + " not found on " + location.publicUrl);
    }

}
