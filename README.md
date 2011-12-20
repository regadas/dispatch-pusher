dispatch-pusher
===============

Scala [dispatch](https://github.com/dispatch/dispatch) publishing library for [Pusher](http://pusher.com)

## Usage

 * using [SBT](https://github.com/harrah/xsbt) on `build.scala` put the following:
    
    ```
    resolvers ++= Seq("regadas repo" at "https://github.com/regadas/repo/raw/master")

    libraryDependencies += "com.regadas" %% "dispatch-pusher" % "0.1"
    ```
    
 * depends on [dispatch](https://github.com/dispatch/dispatch).
    
    ```
    libraryDependencies += "net.databinder" %% "dispatch-http" % "0.8.6"
    ```
    
 * finally ... test example ...
 
    ```scala
    val vars = List("PUSHER_KEY", "PUSHER_SECRET", "PUSHER_APIID")
    val (key :: secret :: apiId :: Nil) = vars.map { v => Option(System.getenv(v)) }

    key aka "PUSHER_KEY env variable" must beSome
    secret aka "PUSHER_SECRET env variable" must beSome
    apiId aka "PUSHER_APIID env variable" must beSome

    //setting pusher credentials
    val pusher = PusherRequest(key.get, secret.get)_
    //specify
    val channel = pusher(apiId.get, "test_channel")
    //specify the event to trigger and the data to be sent ... socket id is optional
    val pushToMyEvent: Request = channel("my_event", "hello world", Some("1"))
    val pushToYourEvent: Request = channel("your_event", "hello world again", None)

    val http = new Http
    val requests = List(pushToMyEvent, pushToYourEvent)
    requests.map { req =>
      val status :Int = http(( req >:> identity) {
        case (status, _, _, _) => status
      })
      status must_==202
    }
    ```