<?php

/**
 * 
 * 1. debug response data
 * 2. follow redirect
 * 3. retrofit timeout
 * 4. send custom header
 * 5. global application context
 * 6. save response cookie / header
 * 7. change url and method from interceptor
 * 8. add custom post and query data
 * 9. converter data response
 * 
 */

// 1. debug response data
// echo json_encode([
//     'status' => 1,
//     'message' => 'hello world!',
//     'data' => [
//         'name' => 'Tester 1',
//         'email' => 'tester1@example.com',
//     ],
// ]);


// 2. follow redirect
// if (! isset($_GET['name'])) {
//     header('Location: custom_retrofit.php?name=Tester');

// } else {
//     echo json_encode([
//         'status' => 1,
//         'message' => 'Redirect',
//         'data' => [
//             'name' => $_GET['name'],
//         ],
//     ]);

// }


// 3. retrofit timeout
// just stop apache server for quick way :->


// 4. send custom header
// echo json_encode([
//     'status' => 1,
//     'message' => 'Get all headers',
//     'data' => [
//         'headers' => getallheaders(),
//     ],
// ]);


// 5. global application context
// do it in android
// session_start();
// if (! $_SESSION) {
//     echo json_encode([
//         'status' => 1,
//         'message' => 'First call session',
//         'data' => [
//             'session' => $_SESSION,
//         ],
//     ]);

//     // put bellow response
//     $_SESSION['name'] = 'Tester 1';
//     $_SESSION['email'] = 'tester1@example.com';

// } else {
//     echo json_encode([
//         'status' => 1,
//         'message' => 'second call session',
//         'data' => [
//             'session' => $_SESSION,
//         ],
//     ]);

// }
// session_destroy();


// 7. change url and method from interceptor
// 8. add custom post and query data
// echo json_encode([
//     'status' => 1,
//     'message' => 'change url and method',
//     'data' => [
//         'request_uri' => $_SERVER['REQUEST_URI'],
//         'method' => $_SERVER['REQUEST_METHOD'],
//         'get_data' => $_GET,
//         'post_data' => $_POST,
//         'content' => file_get_contents('php://input'),
//     ],
// ]);


// 9. converter data response
// echo json_encode([
// 	'code' => 1,
// 	'message' => 'data retrieve',
// 	'data' => [
//         [
// 			'movie_id' => 1,
// 			'name' => 'The Terminator',
// 			'year' => 1984
//         ],
// 		[
// 			'movie_id' => 2,
// 			'name' => 'Terminator 2 : Judgment Day',
// 			'year' => 1991
//         ],
// 		[
// 			'movie_id' => 3,
// 			'name' => 'Terminator 3 : Rise of the Machine',
// 			'year' => 2003
//         ],
//     ],
// ]);