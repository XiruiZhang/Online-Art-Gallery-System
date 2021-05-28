<template>
    <div>
        <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
            <h1 class="display-4">Gallery System</h1>
            <p class="lead">Welcome to Group 10 Gallery System!</p>
        </div>
        <div class="container">
            <div class="card-deck mb-3 text-center">
                <div class="card mb-4 shadow-sm">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">Customer</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">Join for free<small class="text-muted"></small></h1>

			<router-link :to="{name: 'CustomerRegister'}">
                        <button type="button" class="btn btn-lg btn-block btn-primary">Register</button>
			</router-link>
			<br>
			<router-link :to="{name: 'CustomerLogin'}">
                        <button type="button" class="btn btn-lg btn-block btn-primary">Log in</button>
			</router-link>

                    </div>
                </div>
                <div class="card mb-4 shadow-sm">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">Artist</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$15 <small class="text-muted">/ mo</small></h1>
                        <button type="button" class="btn btn-lg btn-block btn-primary">Register</button>
			<br>
                        <button type="button" class="btn btn-lg btn-block btn-primary">Log in</button>
                    </div>
                </div>
                <div class="card mb-4 shadow-sm">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">Admin</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$29 <small class="text-muted">/ mo</small></h1>
                        <button type="button" class="btn btn-lg btn-block btn-primary">Register</button>
			<br>
                        <button type="button" class="btn btn-lg btn-block btn-primary">Log in</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
export default {
  name: 'welcome'
}
</script>
