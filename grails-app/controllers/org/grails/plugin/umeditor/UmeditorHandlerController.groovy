/**
 Copyright Hai-Hua Xiao

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

package org.grails.plugin.umeditor

class UmeditorHandlerController {
    static String[] IMAGE_FILE_TYPES = [".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"] as String[];

    def umeditorConfigService

    def upload() {
        UmeditorUploader up = new UmeditorUploader();
        up.setSavePath(umeditorConfigService.getUploadFolder());
        up.setAllowFiles(IMAGE_FILE_TYPES);
        up.setMaxSize(10000); //单位KB
        up.upload(request);

        String callback = request.getParameter("callback");

        String message = g.message(code: "umeditor.errorinfo.${up.state.name()}", default: '')

        String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.size +", \"state\": \""+ message +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";

        result = result.replaceAll( "\\\\", "\\\\" );

        if( callback == null ){
            render ( result );
        } else{
            render ("<script>"+ callback +"(" + result + ")</script>");
        }
    }

    def images(String path) {
        UmeditorUploader up = new UmeditorUploader();
        up.setSavePath(umeditorConfigService.getUploadFolder());

        File file = new File(up.getPhysicalPath(request, path))
        if(file.file && file.exists()) {
            FileInputStream fis = new FileInputStream(file)
            response.outputStream << fis
        } else {
            response.sendError(HttpURLConnection.HTTP_NOT_FOUND)
        }
    }
}
