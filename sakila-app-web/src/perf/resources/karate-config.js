function fn() {
    var env = karate.env;
    karate.log('karate.env system property was:', env);
    if (!env) {
        env = 'dev';
    }
    var appUrl = karate.properties['app.url'];
    if (!appUrl) {
        appUrl = 'http://localhost:8080';
    }
    var config = {
        appUrl: appUrl
    };
    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    return config;
}
