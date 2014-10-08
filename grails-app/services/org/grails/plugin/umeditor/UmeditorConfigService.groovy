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

import org.springframework.beans.factory.InitializingBean
import org.springframework.web.context.support.ServletContextResource

import javax.servlet.http.HttpServletRequest
import java.util.concurrent.ConcurrentHashMap

class UmeditorConfigService implements InitializingBean {
    def grailsApplication
    def pluginManager

    private pluginVersion
    private umeditorVersion

    private Map<String, String> supportedLang = new ConcurrentHashMap<>()

    String getUploadFolder() {
        "images"
    }

    String resolveLang(HttpServletRequest request) {
        String tag = request.locale.toLanguageTag().toLowerCase()
        String lang = supportedLang.get(tag)
        if(lang) return lang

        String path = ueditorResourcePath + '/lang/' + tag + '/' + tag + '.js'
        ServletContextResource scr = new ServletContextResource(request.servletContext, path)
        lang = scr.exists() ? tag : 'en'
        supportedLang.put(tag, lang)
        return lang
    }

    Umeditor newEditor(def request) {
        new Umeditor(grailsApplication, "${request.contextPath}/${ueditorResourcePath}")
    }

    String getUeditorResourcePath() {
        return "/plugins/${UmeditorConfig.PLUGIN_NAME}-$pluginVersion/${UmeditorConfig.PLUGIN_NAME}-$umeditorVersion"
    }


    @Override
    void afterPropertiesSet() throws Exception {
        pluginVersion = pluginManager.getGrailsPlugin(UmeditorConfig.PLUGIN_NAME)?.version

        // with this new version scheme, version 1.4.3_2 is a plugin patch to ueditor 1.4.3
        if(pluginVersion.contains('_')) {
            umeditorVersion = pluginVersion.substring(0, pluginVersion.indexOf('_'))
        } else {
            umeditorVersion = pluginVersion
        }
    }
}
