import React, { Component } from 'react';
import { Form, Icon, Input, Button, Alert, Row, Col } from 'antd';
import './LoginPage.css';
import * as api from '../../api';

const FormItem = Form.Item;

class LoginPage extends Component {

    constructor(props){
        super(props);
        this.state = {
            loginError: false
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
          if (!err) {
            const auth = {username:values.username, password:values.password};
            api.login(auth)
                .then(resp => {
                    this.props.loginSuccess();
                })
                .catch(err=> {
                    this.setState({loginError:true})
                });
          }
        });
      }
      
    render() {
        const { getFieldDecorator } = this.props.form;
        return (
        <Row align="center">
            <Col >
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {getFieldDecorator('username', {
                        rules: [{ required: true, message: 'Please input your username!' }],
                    })(
                        <Input prefix={<Icon type="user" style={{ fontSize: 13 }} />} placeholder="Username" />
                        )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: [{ required: true, message: 'Please input your Password!' }],
                    })(
                        <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} type="password" placeholder="Password" />
                        )}
                </FormItem>
                {this.state.loginError && <div className="error-message"><Alert message="Login Failure.. Enter correct credentials" type="error" /></div>}
                <FormItem>
                    <Button type="primary" htmlType="submit" className="login-form-button">
                        Log in
                    </Button>

                </FormItem>
            </Form>
            </Col>
            <div className="text-center" >
                <span>There are two predefined user in system. <strong>user1</strong> and <strong>user2</strong> . Their password is <strong>password</strong></span>
            </div>
      </Row>
        );
    }
}

const LoginForm = Form.create()(LoginPage);
export default LoginForm;