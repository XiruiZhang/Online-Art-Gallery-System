<template>
    <div class="text-center">
        <form class="form-signup">
            <h1 class="h3 mb-3 font-weight-normal">Register</h1>
            <p class="hint-text">Please fill all the fields to create your account.</p>

            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" class="form-control" placeholder="Username" required>

            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <label for="confirmPassword" class="sr-only">Confirm Password</label>
            <input type="password" id="confirmPassword" class="form-control" placeholder="Confirm Password" required>
            <label for="address" class="sr-only">Address</label>
            <input type="text" id="address" class="form-control" placeholder="Address" required>
		    <button class="btn btn-lg btn-primary btn-block" type="submit">Register now</button>
            <p>Already have an account? <a href="#/customer-login">Sign in</a></p>
        </form>
	    
    </div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

</script>

<style scoped>
.text-center {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: center;
  align-items: center;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: #f5f5f5;
}
.form-signup {
  width: 100%;
  max-width: 400px;
  padding: 15px;
  margin: auto;
}
.form-signup .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}
.form-signup input[type="email"] {
  margin-bottom: 10px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signup input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.form-signup input[type="text"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.form-signup button{
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>