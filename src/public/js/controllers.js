angular.module("userAccountApp.controllers", []).controller("UserAccountListController", function($scope, $state, popupService, messageService, $window, UserAccount) {
	$scope.userAccounts = UserAccount.query();
	$scope.desc = false;
	$scope.orderColumn = "firstName";
	$scope.successMessage = messageService.get();

	$scope.order = function(orderColumn) {
		$scope.desc = ($scope.orderColumn === orderColumn) ? !$scope.desc : false;
		$scope.orderColumn = orderColumn;
	};

	$scope.deleteUserAccount = function(userAccount) {
		if (popupService.showPopup("Are you sure you want to delete this user account?")) {
			userAccount.$delete(function() {
				$window.location.href = "";
			});
		}
	};
}).controller("UserAccountViewController", function($scope, $stateParams, UserAccount) {
	$scope.userAccount = UserAccount.get({ id: $stateParams.id });
}).controller("UserAccountCreateController", function($scope, $state, $stateParams, messageService, UserAccount) {
	$scope.userAccount = new UserAccount();
	$scope.addUserAccount = function() {
		$scope.userAccount.$save(function() {
			messageService.set("User account has been created successfully!");
			$state.go("userAccounts");
		}, function() {
			alert("Some of the fields contain an error!");
		});
	};
}).controller("UserAccountEditController", function($scope, $state, $stateParams, messageService, UserAccount) {
	$scope.updateUserAccount = function() {
		$scope.userAccount.$update(function() {
			messageService.set("User account has been updated successfully!");
			$state.go("userAccounts");
		}, function() {
			alert("Some of the fields contain an error!");
		});
	};

	$scope.loadUserAccount = function() {
		$scope.userAccount = UserAccount.get({ id: $stateParams.id });
	};

	$scope.loadUserAccount();
});