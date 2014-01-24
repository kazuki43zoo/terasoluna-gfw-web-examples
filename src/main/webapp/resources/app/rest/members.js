(function() {
    "use strict";

    /**
     * Member class.
     */
    var Member = Backbone.Model.extend({
        idAttribute : "memberId",
        initialize : function() {
        }
    });

    /**
     * Collection class of Member.
     */
    var Members = Backbone.Collection.extend({
        model : Member,
        url : "../../../rest/members"
    });

    /**
     * View class of member element.
     */
    var MemberElementView = Backbone.View.extend({
        tmpl : _.template($("#template-member-element-view").html()),
        events : {
            "click .deleteBtn" : "onDelete"
        },
        initialize : function() {
            // _.bindAll(this);
            _.bindAll(this, "render", "render");
            this.listenTo(this.model, "change", this.render);
            this.listenTo(this.model, "destroy", this.onDestroy);
        },
        onDelete : function() {
            this.model.destroy();
        },
        onDestroy : function() {
            this.remove();
        },
        render : function() {
            this.$el.html(this.tmpl(this.model.toJSON()));
            return this;
        }
    });

    /**
     * View class of member list.
     */
    var MemberListView = Backbone.View.extend({
        initialize : function() {
            this.listenTo(this.collection, "add", this.addMemberElementView);
            var _this = this;
            this.collection.fetch({
                reset : true
            }).done(function() {
                _this.render();
            });
        },
        render : function() {
            this.collection.each(function(member) {
                this.addMemberElementView(member);
            }, this);
            return this;
        },
        addMemberElementView : function(member) {
            this.$el.append(new MemberElementView({
                model : member
            }).render().el);
        }
    });

    /**
     * View class of members screen.
     */
    var MembersScreenView = Backbone.View.extend({
        events : {
            "click #addBtn" : "onAdd"
        },
        initialize : function() {
            // _.bindAll(this);
            _.bindAll(this, "render", "render");

            this.$firstName = $("#addForm [name='firstName']");
            this.$lastName = $("#addForm [name='lastName']");
            this.$gender = $("#addForm [name='gender']");
            this.$emailAddress = $("#addForm [name='emailAddress']");

            this.collection = new Members();

            this.memberListView = new MemberListView({
                el : $("#memoList"),
                collection : this.collection
            });

            this.render();
        },
        render : function() {
        },
        onAdd : function() {
            this.collection.create({
                firstName : this.$firstName.val(),
                lastName : this.$lastName.val(),
                gender : this.$gender.val(),
                emailAddress : this.$emailAddress.val()
            }, {
                wait : true
            });
            this.render();
        }
    });

    new MembersScreenView({
        el : $("#main")
    });

}());