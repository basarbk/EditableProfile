import React, { Component } from 'react';
import * as api from '../../api';
import { Form, Input, Select, Row, Col, DatePicker, Button, notification, Tooltip } from 'antd';
import moment from 'moment';
import InputLimiter from './InputLimiter';

const FormItem = Form.Item;
const Option = Select.Option;
const { TextArea } = Input;

const dateFormat = 'YYYY-MM-DD';

const notify = (type, mess, description) => {
    notification[type]({
      message: `${mess}`,
      description: `${description}`
    });
  }

class ProfilePage extends Component {

    constructor(props) {
        super(props);
        this.onInputChange = this.onInputChange.bind(this);
        this.onLocationChange= this.onLocationChange.bind(this);
        this.onSelectLocation = this.onSelectLocation.bind(this);
        this.onResetLocationSelection = this.onResetLocationSelection.bind(this);
        this.onSelectGender = this.onSelectGender.bind(this);
        this.onSelectEthnicity = this.onSelectEthnicity.bind(this);
        this.onSelectReligion = this.onSelectReligion.bind(this);
        this.onSelectFigure = this.onSelectFigure.bind(this);
        this.onSelectMaritalStatus = this.onSelectMaritalStatus.bind(this);
        this.onDateChange = this.onDateChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
        this.state = {
            key:0,
            myprofile: {
                displayName:'',
                realName:'',
                profilePicture:'http://placehold.it/128x128',
                birthday: '2017-09-20',
                gender:'unknown'
            },
            currentCity : undefined,
            static : [],
            errors:[]
        }
    }

    locations = [];

    componentWillMount() {
        api.getMyProfile()
            .then(resp => {
                this.setState({ myprofile: resp.data , key:1, currentCity: resp.data.location.city})
                this.loadStaticData();
            })
            .catch(err => console.log(err));
    }

    loadStaticData = () => {
        api.getStaticData().then(resp => {
            this.setState({static: resp.data});
        }).catch(err => console.log(err));
    }

    onInputChange = (event) => {
        const field = event.target.name;
        const value = event.target.value;
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile[field] = value;
        this.setState({myprofile: Object.assign({}, myprofile)}, () => this.isValid())

    }

    onDateChange = (moment) => {
        const newdate = moment.format(dateFormat);
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.birthday = newdate;
        this.setState({myprofile: Object.assign({}, myprofile)});
    }

    onLocationChange = (event) => {
        this.setState({currentCity: event});
        api.getLocations(event).then(resp => this.locations = resp.data );
    }

    onSelectLocation = (event) => {
        const selectedLoc = this.locations.filter(l => l.city === event);
        if(selectedLoc){
            const myprofile = Object.assign({}, this.state.myprofile);
            myprofile.location = selectedLoc[0];
            this.setState({currentCity:event, myprofile:Object.assign({}, myprofile)});
        } else {
            this.onResetLocationSelection();
        }
    }


    onSelectGender = (gender) => {
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.gender = gender
        this.setState({myprofile:Object.assign({}, myprofile)});
    }

    onSelectEthnicity = (ethnicity) => {
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.ethnicity = ethnicity
        this.setState({myprofile:Object.assign({}, myprofile)});
    }

    onSelectReligion = (religion) => {
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.religion = religion
        this.setState({myprofile:Object.assign({}, myprofile)});
    }

    onSelectFigure = (figure) => {
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.figure = figure
        this.setState({myprofile:Object.assign({}, myprofile)});
    }

    onSelectMaritalStatus = (maritalStatus) => {
        const myprofile = Object.assign({}, this.state.myprofile);
        myprofile.maritalStatus = maritalStatus
        this.setState({myprofile:Object.assign({}, myprofile)});
    }

    onResetLocationSelection = () => {
        this.setState({currentCity: ''+this.state.myprofile.location.city});
    }

    isValid = () => {
        const myprofile = Object.assign({}, this.state.myprofile);
        let errors = {};
        // this is just for demonstration to handle validation on client side.. but to show  the backend validation, skipping it for the front end and 
        // letting backend to reject request if validation fails
        if(myprofile.displayName === ''){
            errors['displayName'] = "This is required"
            this.setState({errors: errors});
            return false;
        } else {
            this.setState({errors: {}});
        }
        return true;
    }

    handleSubmit = (e) => {
        e.preventDefault();
        if(this.isValid()){
            const obj = Object.assign(this.state.myprofile);
            const loc = obj.location;
            delete loc.id;
            obj.location = loc;
            api.updateProfile(obj.id, obj).then(response => {
                notify('success', 'Profile Update', 'completed succesfully');
            }).catch(err => {
                this.setState({errors: err.response.data.errors});
                notify('error', 'Validation Failure', 'Resolve the errors and try again');
            })
        } else {
            notify('error', 'Validation of display name', 'Resolve it and try again');
        }
    }

    render() {
        return (
            <Row>
                <Col span={8} style={{textAlign:'center'}}>
                <img src={this.state.myprofile.profilePicture} alt="Avatar" />
                <br/>
                <span>Photo attachment functionality is not implemented</span>
                </Col>
                <Col span={16}>
                    <Form onSubmit={this.handleSubmit}>
                        <Row gutter={20}>
                            <Col span={16}>
                                <FormItem label="Display Name" required validateStatus={this.state.errors.displayName && 'error'} help={this.state.errors.displayName || ''}>
                                    <Input name="displayName" value={this.state.myprofile.displayName} onChange={this.onInputChange} />
                                </FormItem>
                                <FormItem label="Real Name" required validateStatus={this.state.errors.realName && 'error'} help={this.state.errors.realName || ''}>
                                    <Tooltip
                                        trigger={['focus']}
                                        title={"Your real name won't be displayed to other users"}
                                        placement="rightBottom"
                                        overlayClassName="numeric-input"
                                    >
                                        <Input name="realName" value={this.state.myprofile.realName} onChange={this.onInputChange} />
                                    </Tooltip>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row gutter={20}>
                            <Col span={8}>
                                <FormItem label="Birthday" required validateStatus={this.state.errors.birthday && 'error'} help={this.state.errors.birthday || ''}>
                                    <DatePicker 
                                        key={this.state.key} 
                                        name="birthday" 
                                        defaultValue={moment(this.state.myprofile.birthday, dateFormat)}
                                        disabledDate={(moment) => moment.valueOf() > Date.now()} 
                                        format={dateFormat} 
                                        onChange={this.onDateChange} 
                                        />
                                </FormItem>
                                <FormItem label="Gender" required validateStatus={this.state.errors.gender && 'error'} help={this.state.errors.gender || ''}>
                                    <Select value={this.state.myprofile.gender} placeholder="Select gender" onChange={this.onSelectGender}>
                                        {this.state.static.gender && this.state.static.gender.map((gender, index) =>
                                            <Option key={index} value={gender}>{gender}</Option>
                                        )}
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span={8}>
                                <FormItem label="Height">
                                    <Input name="height" value={this.state.myprofile.height} disabled/>
                                </FormItem>
                                <FormItem label="Figure">
                                    <Select value={this.state.myprofile.figure} placeholder="Select Figure" onChange={this.onSelectFigure}>
                                        {this.state.static.figure && this.state.static.figure.map((figure, index) =>
                                            <Option key={index} value={figure}>{figure}</Option>
                                        )}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>

                        <Row gutter={20}>
                            <Col span={8}>
                                <FormItem label="Marital Status" required validateStatus={this.state.errors.maritalStatus && 'error'} help={this.state.errors.maritalStatus || ''}>
                                    <Tooltip
                                            trigger={['focus']}
                                            title={"Your status won't be displayed to other users"}
                                            placement="rightBottom"
                                            overlayClassName="numeric-input"
                                        >
                                        <Select value={this.state.myprofile.maritalStatus} placeholder="Select Marital Status" onChange={this.onSelectMaritalStatus}>
                                            {this.state.static.maritalStatus && this.state.static.maritalStatus.map((maritalStatus, index) =>
                                                <Option key={index} value={maritalStatus}>{maritalStatus}</Option>
                                            )}
                                        </Select>
                                    </Tooltip>
                                </FormItem>
                            </Col>
                            <Col span={8}>
                            <FormItem label="Location" required validateStatus={this.state.errors.location && 'error'} help={this.state.errors.location || ''}>
                                <Select
                                    mode="combobox" 
                                    value={this.state.currentCity}
                                    placeholder="Select the location"
                                    notFoundContent="Location not found"
                                    defaultActiveFirstOption={false}
                                    showArrow={false}
                                    filterOption={false}
                                    onChange={this.onLocationChange}
                                    onSelect={this.onSelectLocation}
                                    onBlur={this.onResetLocationSelection}
                                >
                                    {this.locations.map((loc, index) => <Option key={index} value={loc.city}>{loc.city}</Option>)}
                                </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row gutter={20}>
                            <Col span={8}>
                                <FormItem label="Religion">
                                    <Select value={this.state.myprofile.religion} placeholder="Select religion" onChange={this.onSelectReligion}>
                                        {this.state.static.religion && this.state.static.religion.map((religion, index) =>
                                            <Option key={index} value={religion}>{religion}</Option>
                                        )}
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span={8}>
                                <FormItem label="Ethnicity">
                                    <Select value={this.state.myprofile.ethnicity} placeholder="Select Ethnicity" onChange={this.onSelectEthnicity} >
                                        {this.state.static.ethnicity && this.state.static.ethnicity.map((ethnicity, index) =>
                                            <Option key={index} value={ethnicity}>{ethnicity}</Option>
                                        )}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row gutter={20}>
                            <FormItem label="Occupation" validateStatus={this.state.errors.occupation && 'error'} help={this.state.errors.occupation || ''}>
                                <Tooltip
                                    trigger={['focus']}
                                    title={"Your occupation won't be displayed to other users"}
                                    placement="bottom"
                                    overlayClassName="numeric-input"
                                >
                                    <TextArea name="occupation" rows={2} value={this.state.myprofile.occupation} onChange={this.onInputChange} />
                                </Tooltip>
                                <InputLimiter limit={256} content={this.state.myprofile.occupation} />
                            </FormItem>
                        </Row>
                        <Row gutter={20}>
                            <FormItem label="About Me"  validateStatus={this.state.errors.aboutMe && 'error'} help={this.state.errors.aboutMe || ''}>
                                <TextArea name="aboutMe" rows={4} value={this.state.myprofile.aboutMe} onChange={this.onInputChange} />
                                <InputLimiter limit={5000} content={this.state.myprofile.aboutMe}/>
                                
                            </FormItem>
                        </Row>
                        <Row gutter={20}>
                            <FormItem>
                                <Button type="primary" htmlType="submit">Update</Button>
                            </FormItem>
                        </Row>
                    </Form>
                </Col>
            </Row>
        );
    }
}


const WrappedProfilePage = Form.create()(ProfilePage);

export default WrappedProfilePage;