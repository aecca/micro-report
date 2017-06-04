const serverApi = "http://localhost:9091/reports";
const notNulls = (key,value) => value === null? undefined : value;

function FormController($scope, $http) {

    $scope.schema = {
        "title": "Reporte",
        "type": "object",
        "properties": {
            "type": {
                "title": "Tipo del Reporte",
                "type": "string",
                "enum": ['HTML', 'XML']
            },
            "name": {
                "title": "Nombre del Reporte",
                "type": "string"
            },
            "content": {
                "title": "Reporte",
                "type": "string",
                "x-schema-form": {
                    "type": "textarea",
                    "placeholder": "Aqui pegar el HTML, XML, etc.."
                }
            },

            "sources": {
                "title": "Datos del Reporte",
                "type": "array",
                "items": {
                    "type": "object",
                    "properties": {
                        "type": {
                            "type": "string",
                            "title": "Tipo",
                            "enum": ['SQL', 'VIEW', 'JSON']
                        },
                        "name": {
                            "type": "string",
                            "title": "Nombre",
                        },
                        "content": {
                            "type": "string",
                            "title": "Contenido"
                        },
                        "collection": {
                            "type": "boolean",
                            "title": "Colleccion?"
                        },
                        "params": {
                            "title": "Parametros del datasource",
                            "descripcion": "Definir parametros del request",
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "name": {"type": "string", "title": "Nombre"},
                                    "type": {
                                        "type": "string",
                                        "title": "Tipo",
                                        "enum": ['STRING', 'INT', 'DOUBLE', 'FLOAT', 'DOUBLE', 'BOOLEAN', 'DATE']
                                    }
                                },
                                "required": []
                            }
                        }
                    },
                    "required": []
                },
            },


        },
        "required": [
            "type",
            "name",
            "content"
        ]
    };

    $scope.form = [
        {
            "type": "section",
            "htmlClass": "row",
            "items": [
                {
                    "type": "section",
                    "htmlClass": "col-xs-6",
                    "items": [
                        "name"
                    ]
                },
                {
                    "type": "section",
                    "htmlClass": "col-xs-6",
                    "items": [
                        "type",
                    ]
                }
            ]
        },

        {
            "key": "content",
            "type": "textarea",
        },
        {
            "type": "fieldset",
            "title": "Datasource",
        },
        {
            "key": "sources",
            "type": "tabarray",
            "add": "Agregar",
            "remove": "Eliminar",
            "style": {
                "remove": "btn-danger"
            },
            "title": "{{ value.name || 'souce'+$index }}",
            "items": [
                {
                    "type": "section",
                    "htmlClass": "row",
                    "items": [
                        {
                            "type": "section",
                            "htmlClass": "col-xs-6",
                            "items": [
                                "sources[].name"
                            ]
                        },
                        {
                            "type": "section",
                            "htmlClass": "col-xs-6",
                            "items": [
                                "sources[].type",
                            ]
                        }
                    ]
                },
                {
                    "key": "sources[].content",
                    "type": "textarea"
                },
                {
                    "key": "sources[].collection",
                    "type": "checkbox"
                },
                {
                    "key": "sources[].params",
                    "add": "New",
                    "remove": "Remove",
                    "style": {
                        "add": "btn-success",
                        "remove": "btn-danger"
                    },
                    "items": [
                        "sources[].params[].name",
                        "sources[].params[].type",
                    ],
                }
            ]
        },
        {
            "type": "fieldset"
        },
        {
            "type": "submit",
            "style": "btn-info btn-large",
            "title": "Guardar"
        }
    ];

    $scope.model = {};

    /**
     * Abrir el editor del reporte
     */
    $scope.openReport = (index) => {
        $scope.model = $scope.reports[index];
    };

    $scope.deleteReport = (reportName) => {
        if (confirm("Esta seguro que desea eliminar el reporte : " + reportName)) {
            $http.delete(serverApi + "/" + reportName)
                .then(data => {
                    alert("Se eliminó correctamente el reporte");
                    $scope.loadReports();
                })
                .catch(err => alert("No se pudo eliminar el reporte"));

        }
    };

    $scope.onSubmit = function (form) {

        console.log(form.$valid);

        $http.post(serverApi, JSON.stringify($scope.model, notNulls))
            .then(data => {
                alert("Se guardo correctamente el reporte");
                $scope.loadReports()
            })
            .catch(err => alert("No se pudo agregar el reporte " + err.message));

    };

    $scope.loadReports = () => {
        $http.get(serverApi)
            .then(response => $scope.reports = response.data)
            .catch(err => console.error(err))
    };

    $scope.loadReports();

}

angular.module('app', ['schemaForm', 'ngSanitize'])
    .controller('FormController', FormController);
