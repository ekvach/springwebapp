<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <script
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script
            src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <h2>Login page</h2>
    <form class="form-horizontal" action="login"
          method="post">

        <div class="form-group">
            <label class="control-label col-sm-2"
                   name="username">Username</label>
            <div class="col-sm-10">
                <input type="text" class="form-control"
                       placeholder="Enter Username" name="username" required/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2"
                   name="password">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control"
                       placeholder="Enter Password" name="password" required/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary" name="ok"
                        value="ok" style="display: inline-block">Login
                </button>
                <button type="reset" class="btn btn-danger" name="clear"
                        value="clear" style="display: inline-block">Clear
                </button>
                <div style="display: inline-block">
                    <a align="right" class="btn btn-link" href="registration"
                       role="button">REGISTER</a>
                </div>
            </div>
            <c:if test="${param.error != null}">
                <h4 style="color: red">Username or password is
                    incorrect</h4>
            </c:if>
        </div>
    </form>
</div>


</body>
</html>