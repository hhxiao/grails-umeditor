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

import grails.util.Environment

class UmeditorTagLib {
    static namespace = "umeditor"

    def grailsApplication
    def pluginManager

    def resources = { attrs ->
        def minified = true
        if (Environment.current != Environment.PRODUCTION) {
            minified = attrs?.minified ? attrs?.minified == 'true' : true
        }
        attrs.remove('minified')
        def editor = new Umeditor(grailsApplication, getPluginResourcePath(request), getPluginVersion(), attrs.remove('lang'))
        out << editor.renderResources(g, minified)
    }
 
    def toolbar = { attrs, body ->
        String type = attrs.remove('type') ?: 'default'
        String value = attrs.value ?: body()
        def editor = new Umeditor(grailsApplication, getPluginResourcePath(request), getPluginVersion())
        out << editor.renderToolbar(g, type, value, attrs)
    }

    def editor = { attrs, body ->
        if (!attrs.id) throwTagError("Tag [editor] is missing required attribute [id]")
        String id = attrs.remove('id')
        String value = attrs.value ?: body()
        def editor = new Umeditor(grailsApplication, getPluginResourcePath(request), getPluginVersion())
        out << editor.renderEditor(g, id, value, attrs)
    }

    private String getPluginResourcePath(def request) {
        return "${request.contextPath}/plugins/${UmeditorConfig.PLUGIN_NAME.toLowerCase()}-$pluginVersion"
    }

    private String getPluginVersion() {
        return pluginManager.getGrailsPlugin(UmeditorConfig.PLUGIN_NAME)?.version
    }
}
