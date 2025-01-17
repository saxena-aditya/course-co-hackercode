<%@ page contentType="text/html; charset = UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Dashboad HackerCode</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/admin-panel/css/main.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loading-btn/loading.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loading-btn/loading-btn.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.css" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous" type="text/javascript"></script>
<link rel="stylesheet" href="https://cdn.plyr.io/3.5.3/plyr.css" />

<script src="https://cdn.plyr.io/3.5.3/plyr.polyfilled.js"
	type="text/javascript"></script>

</head>
<body>
	<section id="header">
		<nav class="navbar navbar-expand-lg navbar-light bg-blue">
			<a class="navbar-brand" href="#"> <i class="fa fa-user"
				aria-hidden="true"></i> <span id='#top-heading'>Student Login</span>
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ml-auto">
					<!--  <li class="nav-item active">
                           <a class="nav-link" href="#">
                               Home <span class="sr-only">(current)</span>
                           </a>
                       </li>
                       <li class="nav-item">
                           <a class="nav-link" href="#">Link</a>
                       </li> -->
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> <img
							src="${ user.getFilePath() }"
							id="user-profile-small" alt="user"> ${ user.getFirstName() }
					</a>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#" data-toggle="modal"
								data-target="#editDetailsModal">Edit Details</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item"
								href="${pageContext.request.contextPath}/logout">Logout</a>
						</div></li>
				</ul>
			</div>
		</nav>
	</section>
	<div class="container-full">
		<div class="wrapper">
			<nav id="sidebar">
				<div class="btns-container">
					<!-- <button class="btn btn-info">
						<i class="fas fa-download"></i>
					</button> -->
					<button class="btn btn-success" data-toggle="modal"
						data-target="#editDetailsModal">
						<i class="far fa-user"></i>
					</button>
					<!-- <button class="btn btn-warning">
						<i class="fas fa-key"></i>
					</button> -->
					<a class="btn btn-danger" style="color: #fff;" id="logout-alt"
						href="${pageContext.request.contextPath}/logout"> <i
						class="fas fa-power-off"></i>
					</a>
				</div>
				<div class="sidebar-header">
					<div class="user-img">
						<div class="img-contain ld-over" id="img-contain">
							<div class="ld ld-ring ld-spin"></div>


							<img
								src="${ user.getFilePath() }"
								alt="" class="user-image-big" id="usr-img">

							<div class="float-upload">
								<input type='file' id="img-inp" /> <i class="fas fa-user-plus"
									id="init-usr-img-upload"></i>
							</div>
						</div>
					</div>
					<div class="student-name">
						<div class="std-name">
							<p id="#name" style="line-height: 1.5em;">${ user.getFirstName() }</p>
						</div>
					</div>
					<div class="info-user">
						<div class="std-id flex">
							<p id="std-name">StudentID:</p>
							<p id="std-id">ST00018</p>
						</div>
					</div>
				</div>
				<ul class="list-unstyled components">
					<li class="active"></li>
					<li><a href="#"> <i class="fas fa-tachometer-alt ia"></i>
							Dashboard

					</a></li>
					<li><a href="#courses" id="courses"> <i
							class="fas fa-file-video ia"></i> My Courses

					</a></li>
					<li><a href="#exams" id="my-exams"> <i
							class="fas fa-edit ia"></i> My Exams

					</a></li>

					<!-- <li><a href="#"> <i class="fas fa-envelope ia"></i> Inbox

					</a></li> -->
					<li><a href="#reports" id="my-reports"> <i
							class="far fa-chart-bar ia"></i> My Reports

					</a></li>

					<!-- <li><a href="#homeSubmenu" data-toggle="collapse"
						aria-expanded="false" class="dropdown-toggle sidebar-a"> <i
							class="fas fa-bars ia"></i> More

					</a>
						<ul class="collapse list-unstyled" id="homeSubmenu">
							<li><a href="#">Home 1</a></li>
							<li><a href="#">Home 2</a></li>
							<li><a href="#">Home 3</a></li>
						</ul></li>
					<li><a href="#"> <i class="fas fa-shopping-cart ia"></i>
							My Packages

					</a></li>
					<li><a href="#"> <i class="fas fa-redo-alt ia"></i>
							Student Login History

					</a></li> -->
					<!-- <li><a href="#pageSubmenu" data-toggle="collapse"
						aria-expanded="false" class="dropdown-toggle sidebar-a"> <i
							class="fas fa-user ia"></i> My Profile
					</a>
						<ul class="collapse list-unstyled" id="pageSubmenu">
							<li><a href="#">Edit Profile</a></li>
							<li><a href="#">Page 2</a></li>
							<li><a href="#">Page 3</a></li>
						</ul></li> -->
					<li><a href="${ pageContext.request.contextPath }/logout">
							<i class="fas fa-sign-out-alt ia"></i> Logout

					</a></li>
				</ul>
			</nav>
			<div class="content ld-over" id="status-arr">
				<div class="ld ld-ring ld-spin"></div>
				<div class="row" style="margin: 0">
					<div class="col-12 grey" style="padding: 0;">
						<div class="top-sec-nav flex">
							<div class="toggle-btn-container">
								<button type="button" id="sidebarCollapse" class="navbar-btn">
									<span></span> <span></span> <span></span>
								</button>
							</div>
							<div class="breadcrums">
								<nav aria-label="breadcrumb">
									<ol class="breadcrumb">
										<li class="breadcrumb-item"><i class="fas fa-home"
											style="margin-right: 5px;"></i> <a href="#">Home</a></li>

										<li class="breadcrumb-item active" aria-current="page"
											id="bread-step">Data</li>
									</ol>
								</nav>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin: 0">
					<div class="top-layer">
						<div class="col-4">
							<div class="title">
								<p>Package Name</p>
							</div>
							<div class="disc">
								<p>Placement Preparation</p>
							</div>
						</div>
						<div class="col-4">
							<div class="title">
								<p>Exam Name</p>
							</div>
							<div class="disc">
								<p>HackWithInfy</p>
							</div>

						</div>
						<div class="col-4">
							<div class="title">
								<p>Package Categories</p>
							</div>
							<div class="disc">
								<p>Placements</p>
							</div>

						</div>
					</div>
					<div class="content-section" id="content"></div>

				</div>
			</div>
		</div>
		<div class="modal" id="editDetailsModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Edit Details</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<form id="submitform" method="POST" action="">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="fname">First Name</label> <input type="text"
											class="form-control" name="firstName" id="fname"
											value="${ user.getFirstName() }" required>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="lname">Last Name</label> <input type="text"
											class="form-control" id="lname" name="lastName"
											value="${ user.getLastName() }" required>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="username">UserName</label> <input type="text"
											class="form-control" id="username" name="username"
											value="${ user.getFirstName() }" required>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="ins">Institute</label> <input type="text"
											class="form-control" id="ins" name="institute"
											value="${ user.getInstitute() }" required>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="Email">Email address</label> <input type="email"
									class="form-control" name="email" id="Email"
									value="${ user.getEmail() }" required>
							</div>

							<div class="form-group">
								<label for="ins">Profile Picture</label> <input type="file"
									class="form-control" id="pic-img" name="file">

							</div>
							<input name="filePath" style="display: none" />
							<div class="modal-footer">
								<input type="submit" id="editSubmit" class="btn btn-success"
									value="Submit">
								<button type="button" class="btn btn-danger"
									data-dismiss="modal">Close</button>
							</div>
						</form>
						<!-- Modal footer -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer
		style="background: black; color: #fff; text-align: center; padding: 10px 0 15px 0px;">
		<span><i class="far fa-copyright"></i> Copyright, 2019</span> <span
			style="position: absolute; right: 60px;"><i
			class="fas fa-envelope"></i> &nbsp;hackercode.19@gmail.com</span>
	</footer>
</body>
</html>



<script
	src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.js"
	type="text/javascript"></script>
<script src="https://sdk.amazonaws.com/js/aws-sdk-2.452.0.min.js"
	type="text/javascript"></script>
<script
	src="${ pageContext.request.contextPath }/resources/js/aws-config.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/admin-panel/js/main.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/admin-panel/js/side-bar-events.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$('#editSubmit').click(function(e) {
		e.preventDefault();
		console.log("Button Clicked");
		var submitForm = $('#submitform')[0];
		var formData = new FormData(submitForm);
		var d = {
			'email' : $('#Email').val(),
			'firstName' : $('#fname').val(),
			'lastName' : $('#lname').val(),
			'username' : $('#username').val(),
			'institute' : $('#ins').val(),
			'file' : $('input[type=file]')[0].files[0]
		}

		$.ajax({
			url : "${pageContext.request.contextPath}/update-user-info",
			type : "POST",
			enctype : 'multipart/form-data',
			data : formData,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
		});
	})

	$(document).on("click", ".hc-indicator", function(e) {
		console.log("it clicked...");
		let original = $(this)[0].id;

		if ($(this).hasClass("fa-plus")) {
			$(this).removeClass("fa-plus");
			$(this).addClass("fa-minus");
		} else if ($(this).hasClass("fa-minus")) {
			$(this).removeClass("fa-minus");
			$(this).addClass("fa-plus");
		}

		$(".hc-indicator").each(function() {
			if (original != $(this)[0].id) {
				if ($(this).hasClass("fa-minus")) {
					$(this).removeClass("fa-minus");
					$(this).addClass("fa-plus");
				}

			}
		});
	});
</script>