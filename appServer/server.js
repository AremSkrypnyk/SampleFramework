var Server = require('Server');
try{
    var webServer = new Server(),
        result = webServer.listen(function (error) {
            console.error('error occured');
            if (error) {
                console.error('Message:' + error.message);
                console.error('Stack' + error.stack);
            }
            console.log(phantom);
            phantom.exit(1);
        });

    if (!result.success) {
        var serverError = "could not create web server listening on port " + result.port;
        console.error(serverError);
        phantom.exit(1);
    } else {
        console.log('server status: Ok');
    }
} catch (e) {
    console.error(e.message);
    phantom.exit(1);
}