# Bienvenidos a JerazPeticiones 

Esta libreria basada en volley, es ideal para ahorrar tiempo de trabajo y desarrollo, consigo trae 4 funciones que son:

* GET

* POST_JSON

* POST_FORM_URL_ENCODED

* POST_MULTIPART_FORM_DATA

* READ_IMAGE


## Comenzar

1. Agregar las dependencias a gradle que son:

* implementation 'co.jeraz.JerazPeticiones:peticiones:0.4'

* implementation 'com.android.volley:volley:1.1.1'

2. sincronizar gradle

## Extender la clase AutoClasificar

Esta clase es la encargada de hacer de filtro, por ejemplo cuando el dispositivo no puede acceder a el servidor y verificas si es problema del dispositivo o del servidor.

```
public class CustomAutoClasificar extends AutoClasificar {
    @Override
    public void Gestionar_respuesta(final Context context, String response, int codigo_peticion, Peticion.EventoPeticion event){
        //aqui haces filtro dependiendo la respuesta del servidor
        //por ejemplo, verifique si la sesion es valida y sino llevarlo a iniciar sesion
    }

    @Override
    public void Gestionar_error(Context context, VolleyError volleyError, int codigo_error,Peticion.EventoPeticion event) {
        //aqui colocas las verificaciones y las acciones en caso de no obtener respuesta del servidor
    }
}
```

## Utilizar la peticion

* Parametros:
    
    * Context es obligatorio
    * String url es obligatorio
    * AutoClasificar puede ser nulo(null)
    * int codigo_peticion es obligatorio
    * Peticion.EventoPeticion es obligatorio
    * Peticion.EstadoPeticion puede ser nulo(null)
    
* Implementacion:
    * Puedes gestionar cada peticion por separado utilizando new Peticion.EventoPeticion y Peticion.EstadoPeticion como a continuacion.
    
    ```
    public class xclase{
    
        private function consultar(){
          Peticion.GET(context, url, new CustomAutoClasificar, 0, new Peticion.EventoPeticion() {
                  @Override
                  public void Response(String respuesta, int codigo_peticion) {
              
                  }

                   @Override
                  public void Error(VolleyError error, int codigo_peticion) {
                                
                  }
              }, new Peticion.EstadoPeticion() {
                  @Override
                  public void Iniciada() {
                        //cuando la peticion inicia
                  }

                  @Override
                  public void Terminada() {
                        //cuando termina
                  }
          });
        }
        
    }
    ```
    
    * utilizar this e implementarla(implements) en su clase.
    
    ```
    public class xclase implements Peticion.EventoPeticion, Peticion.EstadoPeticion{
    
        private function consultar(){
          Peticion.GET(context, url, new CustomAutoClasificar, 0, this, this);
        }
        
        @Override
        public void Response(String respuesta, int codigo_peticion) {
        
        }

        @Override
        public void Error(VolleyError error, int codigo_peticion) {

        }

        @Override
        public void Iniciada() {
                //cuando la peticion inicia
        }

        @Override
        public void Terminada() {
                //cuando termina
        }
    }
    ```
    
## Peticiones con sus parametros

* GET:
    
    ```
    Peticion.GET(context, url, new CustomAutoClasificar, 0, this, this);
    ```
    
* POST JSON:
    
    ```
    JSONObject parametros = new JSONObject();
    parametros.put("parametro_1", "valor");
    
    Peticion.POST_JSON(context, url, new CustomAutoClasificar, 0, parametros, this, this);
    ```
    
* POST FORM URL ENCODED:
    
    ```
    Map<String, String> map_parametros = new HashMap<>();
    map_parametros.put("parametro_1", "valor");
    
    Peticion.POST_FORM_URL_ENCODED(context, url, new CustomAutoClasificar, 0, map_parametros, this, this);
    ```
    
* POST MULTIPART FORM DATA:
    
    ```
    Map<String, String> map_parametros = new HashMap<>();
    map_parametros.put("parametro_1", "valor");
    
    Map<String, ParteDatos> archivos = new HashMap<>();
    archivos.put("imagen",new ParteDatos("imagen", Funciones.Bitmap_to_byte(imagen_seleccionada),"image/png"));
    
    Peticion.POST_MULTIPART_FORM_DATA(context, url, new CustomAutoClasificar, 0, map_parametros, archivos,this, this);
    ```
    
* READ IMAGE:
    
    ```
    Peticion.READ_IMAGE(context, url, new Peticion.EventoReadImage() {
        @Override
        public void Start() {
            //cuando se inicia la busqueda
        }

        @Override
        public void Downloaded(Bitmap resultado) {
            //la imagen fue descargada
        }

        @Override
        public void Error(String error) {
            //error, por ejemplo 404( not found)
        }
    });
    ```
