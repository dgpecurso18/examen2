# examen2
Descripcion del proceso para escalarse a 3

1. En rancher se agregan 3 clientes
2. Agregar el servicio en rancher, se le pone nombre (Cuantos) y la imagen que va a ejecutar (dgpecurso18/examen2).
3. Una vez activo el servicio, se agrega un balanceador
   - Configurando nombre (Cuantos), puerto en el que va a escuchar el balanceador (10001) y puerto en el que se levantó el servicio. (8080)
4. Una vez activo el balanceador, en elsimbolo i, que sigue al nombre del balanceador, se escala el servicio a 3.
