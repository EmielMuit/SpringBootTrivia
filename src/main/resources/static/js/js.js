var app = angular.module('triviaApp', []);
app.controller('triviaCtrl', function ($scope, $http) {
    $scope.questionAlert = function ()
    {
        alert("You did not answer all questions");
    }
    
    $scope.postdata = function () {
        if ($scope.trivia !== undefined)
        {
            var numberOfQuestions = document.querySelectorAll(".triviaquestion").length;

            var answers = new Array(numberOfQuestions);
            var i;
            for (i = 0; i < numberOfQuestions; i++)
            {
                if ($scope.trivia.question["answer"+i]!== undefined)
                {
                    answers[i] = $scope.trivia.question["answer"+i];
                }
                else
                {
                    $scope.questionAlert();
                    return;
                }
            }
            delete $scope.trivia;

            var answerJSON = JSON.stringify(answers);

            $http.post('/checkanswers', answerJSON).then(function (response) {
                if (response.data)
                {
                    var i;
                    var results = "Results: ";
                    for (i = 0; i < response.data.length; i++)
                    {
                        results += "\nQuestion " + (i+1) + ": ";
                        if (response.data[i] == true)
                        {
                            results += "CORRECT!";
                        }
                        else
                        {
                            results += "false.";
                        }
                    }
                    alert(results);
                    location.reload();
                }
            }, function (response) {
                console.log("Something went wrong");
                location.reload();
            });
        }
        else
        {
            $scope.questionAlert();
        }
    };
});