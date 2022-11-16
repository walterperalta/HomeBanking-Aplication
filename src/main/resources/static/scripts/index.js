Vue.createApp({
    data(){
        return{
            email : '',
            password : '',
            noRegistrado : false,
            form : true,
            newUseFirstName : '',
            newUserLastName : '',
            newUserEmail : '',
            newUserPassword : '',
            erro : false

        }
    },
    created(){

    },
    methods : {
        singIn(){
            let self = this;
            // axios.post('http://localhost:8080/api/login',`email=${this.email}&password=${this.password}`)
            axios.post('https://home-banking-mh.herokuapp.com/api/login',`email=${this.email}&password=${this.password}`)
                .then(response => {
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    this.noRegistrado = true
                  });
        },
        formRegistro(){
            if (this.form){
                this.form = false
                this.noRegistrado = false
            }else{
                this.form = true
            }
        },
        register(){
            // axios.post('http://localhost:8080/api/clients',`firstName=${this.newUseFirstName}&lastName=${this.newUserLastName}&email=${this.newUserEmail}&password=${this.newUserPassword}`)
            axios.post('https://home-banking-mh.herokuapp.com/api/clients',`firstName=${this.newUseFirstName}&lastName=${this.newUserLastName}&email=${this.newUserEmail}&password=${this.newUserPassword}`)
                .then(response => {
                    this.email = this.newUserEmail
                    this.password = this.newUserPassword
                    this.singIn()
                })
                .catch(error => {
                    this.erro = true
                })
        },
        back(){
            this.form = true
        }

    },
    computed : {

    }
}).mount('#app')
console.log("index.js")