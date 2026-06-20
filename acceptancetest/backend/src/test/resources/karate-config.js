function fn() {
  var env = karate.env; // obtener la propiedad del sistema 'karate.env'
  if (!env) {
    env = 'dev';
  }
  
  var config = {
    baseURL: 'http://localhost:8080/api'
  };

  karate.configure('connectTimeout', 15000);
  karate.configure('readTimeout', 15000);
  
  return config;
}
