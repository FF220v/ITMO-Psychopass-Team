Before merging a pull request, double check if everything is done:

For documentation changes:
- [ ] Changes reviewed and approved

For changes in tests following and above:
- [ ] Acceptance tests passed
- [ ] Performance tests passed

For bug fixes and features following and above:
- [ ] Have acceptance tests and (optional) performance tests
- [ ] Acceptance tests cover happy and unhappy paths
- [ ] No PII in logs
- [ ] Deployment in dev environment is successful
- [ ] A new feature/bugfix has a user story (see `user_stories/us_template.md`)
- [ ] A new feature/bugfix matches it's userstory
- [ ] Exploratory tested (see checklist below)

Exploratory testing checklist:
- [ ] Happy and unhappy paths on changes are tested
- [ ] Security check a: Citizen don't see devices.
- [ ] Security check b: Citizen don't see info of another person. 