function click(){
	console.log('CALLED');
	fetch('www.google.com').then(response=>{
		console.log(response);
	});
}
function myFunction() {
  document.getElementById("demo").innerHTML = "YOU CLICKED ME!";
  let username = document.getElementById('username').value;
  let password = document.getElementById('password').value;
  console.log(username);
  console.log(password);
  console.log('CALLED');
  console.log(JSON.stringify({username,password}))
	fetch('srs/rest/user/register', {
		method: 'POST',
		body: JSON.stringify({username,password}),
		headers: {
	      'Content-Type': 'application/json'
	      // 'Content-Type': 'application/x-www-form-urlencoded',
	    }
		
	})
	.then(data=>console.log('data', data))
	.catch(error=>console.log('error', error))
}
console.log('IMPORTED');