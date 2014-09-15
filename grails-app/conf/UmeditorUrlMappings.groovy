class UmeditorUrlMappings {
    static mappings = {
        "/umeditorHandler/images/$path**?"(controller: "umeditorHandler") {
            action = [GET: 'images']
        }

        "/umeditorHandler/$action"(controller: "umeditorHandler") {
        }
    }
}
