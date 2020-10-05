<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		<h2>Edit User</h2>
		<form:form action="edituser" method="post" modelAttribute="user"
			class="form-horizontal">

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="id">ID</form:label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control" path="id"
						value="${user.id}" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="username">Username</form:label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control" path="username"
						value="${user.username}" readonly="true" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="password">Password</form:label>
				<div class="col-sm-10">
					<form:input type="password" class="form-control"
						value="${user.password}" placeholder="Enter Password"
						path="password" required="true" />
					<form:errors path="password" style="color: red" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="confirmPassword">Confirm
                Password</form:label>
				<div class="col-sm-10">
					<form:input type="password" class="form-control"
						value="${user.password}" placeholder="Enter Password again"
						path="confirmPassword" required="true" />
					<c:if test="${isPassTheSame == false}">
						<p style="color: red">Password is not the same</p>
					</c:if>
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="email">Email</form:label>
				<div class="col-sm-10">
					<form:input type="email" class="form-control"
						placeholder="Enter Email" path="email" value="${user.email}"
						required="true" />
					<c:if test="${isEmailExists == true}">
						<p style="color: red">The email you've entered is already
							exist. Please reenter</p>
					</c:if>
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="firstName">First Name</form:label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control"
						placeholder="Enter First Name" path="firstName"
						value="${user.firstName}" required="true" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="lastName">Last Name</form:label>
				<div class="col-sm-10">
					<form:input type="text" class="form-control"
						placeholder="Enter Last Name" path="lastName"
						value="${user.lastName}" required="true" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="birthday">Birthday</form:label>
				<div class="col-sm-10">
					<form:input type="date" class="form-control" path="birthday"
						value="${user.birthday}" required="true" />
					<form:errors path="birthday" style="color: red" />
				</div>
			</div>

			<div class="form-group">
				<form:label class="control-label col-sm-2" path="userRole.name">Role</form:label>
				<div class="col-sm-10">
					<form:select name="role" class="form-control" required="true"
						path="userRole.name">
						<option value="${user.userRole.name}" selected>${user.userRole.name}</option>

						<c:forEach items="${roleList}" var="role">
							<c:if test="${role.name ne user.userRole.name}">
								<option>${role.name}</option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>
			</div>


			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10" style="display: inline">
					<div style="display: inline-block">
						<button type="submit" class="btn btn-primary" name="ok" value="ok">OK
						</button>
					</div>
					<div style="display: inline-block">
						<a class="btn btn-danger" href="homepageadmin" role="button">Cancel</a>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>