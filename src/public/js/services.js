angular.module("userAccountApp.services", []).factory("UserAccount", function($resource) {
    return $resource("http://localhost:8080/UserAccountManager/userAccounts/:id", {id: "@id"}, {
        update: {
            method: "PUT"
        }
    });
}).service("popupService",function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
}).service("messageService", function() {
	 var successMessage = "";
	 
	 function set(data) {
		 successMessage = data;
	 }
	 
	 function get() {
		 return successMessage;
	 }
	 
	 return {
		 set: set,
		 get: get
	 }
});