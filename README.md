# NoN Identity Bridge

Mod Fabric (MC 1.21.11) que hace que **Needs of Nature** trate a un jugador/IA (Numen) morfeado con **Identity2**
como el mob en el que se transformó (animaciones del mob en vez de las humanas).

## Compilar con GitHub (sin instalar nada)
1. Crea un repo en github.com (puede ser privado) y sube TODO el contenido de esta carpeta (incluida `.github`).
2. Ve a la pestaña **Actions** -> workflow **build** (se ejecuta solo con cada push; o "Run workflow").
3. Cuando termine en verde, entra a la ejecución -> **Artifacts** -> descarga `nonidentitybridge`.
4. Dentro del zip está `nonidentitybridge-1.0.0.jar` (ignora el `-sources`). Ponlo en `mods/`.

Si falla, copia el log rojo del paso **Build** y pásamelo.

## Qué hace
- `ActorCompatibilityMixin`: `matchingTypes` devuelve el id del mob (sin rol `player`).
- `McaCompatPlayerRigMixin`: `supportsPlayerRig` = false si está morfeado.
- `AnimationDefinitionsMixin`: redirige los `getType()` directos de NoN.
- `RenderDispatcherMixin` (cliente, experimental): corrige el `entityTypeId` del render state.
