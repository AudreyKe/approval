import Cookies from 'js-cookie';

const user = {
    state: {},
    mutations: {
        logout (state, vm) {
            localStorage.setItem('menu','');
            localStorage.setItem('token','');
        }
    }
};

export default user;