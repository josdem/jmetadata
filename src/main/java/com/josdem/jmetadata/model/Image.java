/*
   Copyright 2025 Jose Morales contact@josdem.io

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.josdem.jmetadata.model;

import lombok.Data;

import java.util.List;

@Data
public class Image {
    private List<String> types;
    private boolean front;
    private boolean back;
    private int edit;
    private String image;
    private String comment;
    private boolean approved;
    private List<Thumbnail> thumbnails;
    private String id;
}