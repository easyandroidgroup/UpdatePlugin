/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.model;

/**
 * Parse http response data to Update class
 *
 * @author lzh
 */
public interface UpdateParser {

    /**
     * Parse http response data to Update class
     *
     * @param httpResponse The response data to be parsed
     * @return a new Update instance
     * @throws Exception Throws a exception if a error occurs.
     */
    Update parse(String httpResponse) throws Exception;
}
