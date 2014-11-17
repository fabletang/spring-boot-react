var DepartmentSelect = React.createClass({
    getInitialState: function() {
        return {options: []};
    },
    componentDidMount: function() {
        // get your data
        $.ajax({
            url: "/course/dept",
            success: this.successHandler
        })
    },
    successHandler: function(data) {
        // assuming data is an array of {name: "foo", value: "bar"}
        //alert(data[0].deptCode)
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            //alert(option)
            this.state.options.push(
                <option key={i} value={option.deptCode}>{option.departmentTitle}</option>
            );
        }
        //alert(this.state.options)
    },
    render: function() {
         <select name="deptField">
             //need to fix this to print options
         {this.state.options}

         </select>

    }
});

var Course = React.createClass({
    queryData: function() {

        $.ajax({
            url: this.props.url,
            data:
            {
                term: $("#termField").val(),
                deptCode: $("#deptField").val()
            },
            success: function(data) {
                this.setState({data: data});
            }.bind(this)
        });
    },
    getInitialState: function() {
        return {data: []};
    },
    onClick: function(e) {
        //alert($("#termField").val());
        //alert($("#deptField").val());
        $.ajax({
            url: this.props.url,
            data:
            {
                term: $("#termField").val(),
                deptCode: $("#deptField").val()
            },
            success: function(data) {
                this.setState({data: data});
            }.bind(this)
        });
    },
    //componentDidMount: function() {
        //this.getGreetingFromServer();
        //setInterval(this.getGreetingFromServer, this.props.pollInterval);
    //},
    render: function() {
        return (
            <div>
                <select id="termField" name="termField">
                    <option value="F2014">Fall 2014</option>
                    <option value="S2015" selected="selected">Spring 2015</option>
                </select>
                <DepartmentSelect />

                <button type="button" onClick={this.onClick} value="Search">Search</button>

                <br/><br/><hr/>
                <span>{this.state.data}</span>
            </div>
            );
    }
});
React.render(
    <Course url="/course/basic" />,
    document.getElementById('content')
);
