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

class Umeditor {
    def config
    def basePath
    def version
    def lang
    def urlHandlers

    Umeditor(def grailsApplication, String basePath, String version) {
        this(grailsApplication, basePath, version, '')
    }

    Umeditor(def grailsApplication, String basePath, String version, String lang) {
        this.config = new UmeditorConfig(grailsApplication)
        this.basePath = basePath
        this.version = version
        this.lang = lang ?: 'en'
    }

    def renderResources(g, minified) {
        def umeditorHome = "${basePath}/umeditor-${version}"
        return """
<link href="${umeditorHome}/themes/default/css/umeditor${minified ? '.min' : ''}.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${umeditorHome}/umeditor.config.js"></script>
<script type="text/javascript" src="${umeditorHome}/third-party/JSON.js"></script>
<script type="text/javascript" src="${umeditorHome}/umeditor${minified ? '.min' : ''}.js"></script>
<script type="text/javascript" src="${umeditorHome}/lang/${lang}/${lang}.js"></script>
<script type="text/javascript">
    window.UMEDITOR_CONFIG.home = "${umeditorHome}/";
    window.UMEDITOR_CONFIG.imageUrl = "${g.createLink(controller: 'umeditorHandler', action: 'upload')}";
    window.UMEDITOR_CONFIG.imagePath = "${g.createLink(controller: 'umeditorHandler', action: 'images')}?path=";
</script>
"""
    }

    def renderEditor(String instanceId, String instanceName, String initialValue, Map attrs) {
        StringBuilder buf = new StringBuilder()
        buf << """
<textarea id="${instanceId}" name="${instanceName}" style="${attrs.remove('style')}" class="${attrs.remove('class')}">${initialValue}</textarea>
<script type="text/javascript">
    var um_${instanceId} = UM.getEditor("${instanceId}");
</script>\n"""
        return buf.toString()
    }
}