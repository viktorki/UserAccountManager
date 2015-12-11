angular.module("userAccountApp", ["ui.router", "ngResource", "userAccountApp.controllers", "userAccountApp.services"]);

angular.module("userAccountApp").config(function($stateProvider) {
	$stateProvider.state("userAccounts", {
		url: "/userAccounts",
		templateUrl: "partials/userAccounts.html",
		controller: "UserAccountListController"
	}).state("newUserAccount", {
		url: "/userAccounts",
		templateUrl: "partials/user-account-add.html",
		controller: "UserAccountCreateController"
	}).state("editUserAccount", {
		url: "/userAccounts/:id",
		templateUrl: "partials/user-account-edit.html",
		controller: "UserAccountEditController"
	});
}).run(function($state) {
	$state.go("userAccounts");
});