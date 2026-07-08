# EntreLinhas

API desenvolvida em Spring Boot para extração, processamento e interpretação de textos utilizando OCR e Inteligência Artificial.

## Pré-requisitos

Antes de executar o projeto, instale:

* Java 22
* Maven 3.9+
* Tesseract OCR (com o idioma `por`)
* Ollama

Baixe o modelo utilizado pela aplicação:

```bash
ollama pull llama3
```

Inicie o Ollama:

```bash
ollama serve
```

## Configuração

Crie um arquivo `.env` na raiz do projeto utilizando o `.env.example` como base.

Exemplo:

```env
UPLOAD_DIR=uploads
PROCESSED_DIR=processed

OLLAMA_URL=http://localhost:11434
OLLAMA_MODEL=llama3

TESSERACT_DATA_PATH=C:\Program Files\Tesseract\tessdata
TESSERACT_LANGUAGE=por
TESSERACT_PAGE_SEG_MODE=3
TESSERACT_OCR_ENGINE_MODE=3
```

## Executando o projeto

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
cd entrelinhas
```

Compile o projeto:

```bash
mvn clean install
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8002
```

## Observações

* O Ollama deve estar em execução e com o modelo `llama3` instalado.
* O Tesseract deve possuir o idioma português (`por`) instalado.
* Caso utilize outro sistema operacional, ajuste a variável `TESSERACT_DATA_PATH` no arquivo `.env`.
