# CDME-javascript
Remodelagem do Projeto Ótimo, do CDME UFF, agora com as construções GeoGebra disponíveis em javascript em HTML5

# Estrutura do Repositório:

## Páginas do Projeto Ótimo
As pastas com nome de 3 caracteres são as relativas às páginas do Projeto Ótimo, já devidamente alteradas para o funcionamento com HTML5. 

### Páginas com construções 3D
Foram mantidos todos os arquivos relativos ao JavaView, caso haja algum interesse futuro em reimplementar as contruções por esses meios.

A implementação pelo GeoGebra foi feita por meio de um arquivo .js com a injeção da construção e todo o JavaScript específico dessas construções. No módulo [epi](/epi), por exemplo, a injeção da construção 3D está no arquivo [piramide-inscrito-esfera.js](/epi/epi-html/piramide-inscrito-esfera.js)

## Pastas auxiliares

De modo a organizar melhor o progresso, criamos pastas auxiliares com alguns arquivos novos, ou antigos retirados das páginas originais do Projeto Ótimo.

### app-geogebra

Contém o app GeoGebra original, executável pelo arquivo [geogebra.jar](/app-geogebra/geogebra.jar)

### geogebra-constructions

Contém as contruções originais do Projeto Ótimo, em formato .ggb, e em formato .html, exportado no modelo antigo de applet Java, de onde tiramos os códigos de identificação ggbBase64 necessários para a implementação atual.

### novas_construc-3d

Contém os códigos das novas construções 3D feitas para substituir os antigos arquivos JavaView.
