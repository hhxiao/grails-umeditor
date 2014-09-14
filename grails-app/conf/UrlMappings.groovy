class UrlMappings {

	static mappings = {
        "/umeditorHandler/images/$path**?"(controller: "umeditorHandler") {
            action = [GET: 'images']
        }

        "/umeditorHandler/$action"(controller: "umeditorHandler") {
        }

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
