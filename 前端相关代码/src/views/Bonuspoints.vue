<template>
	<div class="wrapper">
	    <Header>
		    <p>我的积分</p>
		</Header>	
	    <div class="card">
	        <p>积分</p>
	    </div>
		<div class="box-below">
		    <div class="card-body">
	            <div class="col-6">积分余额：{{bonuspoints.balance}}</div>
    	    </div>
		    <button class="charge"@click="checktranscation">查看流水</button>
		</div>
	</div>
</template>

<script>
	export default {
	name: "Bonuspoints",
	data() {
			return {
	// 			userId: this.$route.query.userId,
	 			bonuspoints: {}
			}
	 	},
		created() {
		         this.user = this.$getSessionStorage('user');
				 this.$axios.post('BonuspointsController/getBonuspointsbyuserId', this.$qs.stringify({
				 userid: this.user.userId,
				 })).then(response => {
				   this.bonuspoints = response.data;
				   // this.$setSessionStorage('virtualWallet', virtualWallet);
				  // console.log(this.VirtualWallet);
				   }).catch(error => {
				        console.error(error);
				    });
		},
		methods:{
			checktranscation(){
				this.$router.push({
					path: '/Bonuspointstranscation',
				});
			}
		}
	}
</script>

<style scoped>
.wrapper {
    width: 100%;
	height: 100%;
	background-image: linear-gradient(to top, rgba(255, 0, 0, 0), rgb(59, 176, 246));
}

.wrapper Header {
  display: flex;
  align-items: center;
  height: 7vw;
  font-weight: 550;
  color: #FFFFFF;
  padding-top: 3vw;
}

.card {
    display: flex;
    align-items: center;
    justify-content: space-between;   
    height: 9vw;   
    font-size: 3.5vw;
    color: #FFFFFF;
    background-color: #359cff;
    margin: 2vw 3vw 0 3vw;   
    border-radius: 10px 10px 0 0;
}

.box-below {
  display: flex;
  flex-direction: column;
  height: 35vw;
  font-size: 3vw;
  color: black;
  background-color: #FFFFFF;
  margin: 0 3vw 0 3vw;
  border-radius: 10px;
  border: solid 1px #DDD;
  bottom: 5vw;
}

.card-body {
	margin-left: 3vw;
	flex: 1;
	border-right: solid 1px #DDD;
}

.charge {
  height: 6vw;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 3vw;
  margin-top: 27vw;
  border-radius: 10px;
  color: #ffffff;
  background-color: #359cff;
  position: absolute;
  border: none;
}

</style>