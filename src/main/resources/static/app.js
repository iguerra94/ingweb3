// 1 parametro es getter => trae el modulo
// 2 parametros es setter => crea el modulo
angular.module('iw3', []);

var app = angular.module('iw3');

app.controller("div1.controller", 
	function ($scope) {
		$scope.titulo = "Hola desde el controlador 1";
		$scope.opciones = {"color": "black", "tamanio": 12};
	}
);

app.controller("div2.controller", 
	function ($scope) {
		$scope.titulo = "Hola desde el controlador 2";
	}
);