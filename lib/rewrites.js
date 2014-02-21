module.exports = [
    {from: '/static/*', to: 'static/*'},
    {from: '/', to: '_list/view_checkpoints/checkpoints_and_measurement'},

    {from: '/view_checkpoints_list', to: '_list/view_checkpoints/checkpoints_and_measurement'},
    {from: '/measurements_list', to: '_list/checkpoint_details/checkpoints_and_measurement'},
    {from: '/edit_checkpoints_list', to: '_list/checkpoint_edit/checkpoints_all'},
    {from: '/activate_checkpoints_list', to: '_list/checkpoint_activate/checkpoints_deactivated'},
    {from: '/deactivate_checkpoints_list', to: '_list/checkpoint_deactivate/checkpoints'},

    {from: '/new_checkpoint_show', to: '_show/edit_checkpoint'},
    {from: '/edit_checkpoint_show', to: '_show/edit_checkpoint'},
    {from: '/edit_checkpoint_show/*', to: '_show/edit_checkpoint/*'},

    {from: '/edit_checkpoint_update', to: '_update/edit_checkpoint', method: 'POST'},
    {from: '/edit_checkpoint_update/*', to: '_update/edit_checkpoint/*', method: 'POST'},

    {from: '/activate_checkpoint_update/*', to: '_update/activate_checkpoint/*', method: 'POST'},
    {from: '/deactivate_checkpoint_update/*', to: '_update/deactivate_checkpoint/*', method: 'POST'}
];