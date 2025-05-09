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

package com.josdem.jmetadata.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FormatterHelper {

  public String getBasicFormat(String word) {
    if (word.length() == 2) {
      return word;
    }
    String formatted = word.replace("-", StringUtils.EMPTY);
    formatted = formatted.replace("(", StringUtils.EMPTY);
    formatted = formatted.replace(")", StringUtils.EMPTY);
    formatted = formatted.replace("0", StringUtils.EMPTY);
    formatted = formatted.replace("1", StringUtils.EMPTY);
    formatted = formatted.replace("2", StringUtils.EMPTY);
    formatted = formatted.replace("3", StringUtils.EMPTY);
    formatted = formatted.replace("4", StringUtils.EMPTY);
    formatted = formatted.replace("5", StringUtils.EMPTY);
    formatted = formatted.replace("6", StringUtils.EMPTY);
    formatted = formatted.replace("7", StringUtils.EMPTY);
    formatted = formatted.replace("8", StringUtils.EMPTY);
    formatted = formatted.replace("9", StringUtils.EMPTY);
    formatted = formatted.replace("'", StringUtils.EMPTY);
    formatted = formatted.replace(",", StringUtils.EMPTY);
    formatted = formatted.replace("I", StringUtils.EMPTY);
    formatted = formatted.replace("&", StringUtils.EMPTY);
    return formatted.replace(" ", StringUtils.EMPTY);
  }
}
