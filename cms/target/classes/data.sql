INSERT INTO portfolio_users (id, first_name, last_name, email) VALUES
                                                                   (1, 'Alex', 'Chen', 'alex.chen@example.com'),
                                                                   (2, 'Jordan', 'Patel', 'jordan.patel@example.com'),
                                                                   (3, 'Taylor', 'Nguyen', 'taylor.nguyen@example.com'),
                                                                   (4, 'Morgan', 'Singh', 'morgan.singh@example.com'),
                                                                   (5, 'Casey', 'Kim', 'casey.kim@example.com'),
                                                                   (6, 'Riley', 'Garcia', 'riley.garcia@example.com');

INSERT INTO skills (id, name) VALUES
                                  (1, 'Java'),
                                  (2, 'Spring Boot'),
                                  (3, 'SQL'),
                                  (4, 'REST API'),
                                  (5, 'Git'),
                                  (6, 'Python'),
                                  (7, 'Data Analysis'),
                                  (8, 'Power BI'),
                                  (9, 'Figma'),
                                  (10, 'HTML/CSS'),
                                  (11, 'JavaScript'),
                                  (12, 'React'),
                                  (13, 'Machine Learning'),
                                  (14, 'Project Management'),
                                  (15, 'Tableau');

INSERT INTO projects (id, title, overview, result, user_id) VALUES
                                                                (1, 'Career Portfolio Platform', 'Web application backend for managing projects, skills, and job applications', 'Implemented core backend structure and entity relationships', 1),
                                                                (2, 'Healthcare Operations Dashboard', 'Analytics dashboard for monitoring service flow and performance metrics', 'Improved visibility into bottlenecks and operational trends', 1),
                                                                (3, 'Transit Wayfinding Redesign', 'Human factors project focused on improving navigation and signage clarity', 'Produced redesigned layouts and usability recommendations', 2),
                                                                (4, 'Sales Reporting Automation', 'Automated reporting workflow for recurring business performance reviews', 'Reduced manual reporting effort and improved consistency', 2),
                                                                (5, 'E-commerce Recommendation Tool', 'Prototype recommendation engine for personalized product suggestions', 'Built and evaluated a basic recommendation model', 3),
                                                                (6, 'Student Success Analytics', 'Data project analyzing academic performance patterns and risk factors', 'Generated insights for targeted support strategies', 3),
                                                                (7, 'Job Application Tracker', 'System for tracking applications, statuses, and linked projects', 'Enabled organized monitoring of recruiting progress', 4),
                                                                (8, 'Personal Finance Dashboard', 'Interactive dashboard for budgeting and spending analysis', 'Improved financial tracking and category breakdown visibility', 4),
                                                                (9, 'Inventory Management System', 'Tool for tracking inventory levels, stock movement, and reorder points', 'Improved stock visibility and reduced manual tracking', 5),
                                                                (10, 'Customer Feedback Analysis', 'Text and sentiment analysis on customer reviews and survey responses', 'Identified recurring service issues and improvement opportunities', 5),
                                                                (11, 'Portfolio Website Redesign', 'Modernized personal website with project showcase and recruiter view', 'Improved visual presentation and navigation', 6),
                                                                (12, 'Marketing Campaign Performance Dashboard', 'Dashboard for comparing campaign reach, engagement, and conversion', 'Helped evaluate campaign effectiveness across channels', 6);

INSERT INTO job_applications (id, company, role, application_date, status, notes, user_id) VALUES
                                                                                    (1, 'TechNova', 'Software Engineer Intern', '2026-03', 'Applied', 'Highlighted backend and API development projects', 1),
                                                                                    (2, 'DataBridge', 'Data Analyst Intern', '2026-03', 'Interview', 'Selected dashboard and analytics-focused projects', 1),
                                                                                    (3, 'UrbanLink', 'UX Research Intern', '2026-02', 'Applied', 'Referenced wayfinding and redesign experience', 2),
                                                                                    (4, 'InsightWorks', 'Business Analyst Intern', '2026-03', 'Applied', 'Linked reporting automation and operations projects', 2),
                                                                                    (5, 'RetailAI', 'Machine Learning Intern', '2026-02', 'Interview', 'Emphasized recommendation and analytics projects', 3),
                                                                                    (6, 'LearnSphere', 'Product Analyst Intern', '2026-03', 'Applied', 'Included student analytics and dashboard work', 3),
                                                                                    (7, 'CareerFlow', 'Full Stack Developer Intern', '2026-03', 'Applied', 'Showcased tracking system and dashboard projects', 4),
                                                                                    (8, 'FinEdge', 'Data Visualization Intern', '2026-02', 'Rejected', 'Used finance dashboard and visualization examples', 4),
                                                                                    (9, 'SupplySync', 'Operations Analyst Intern', '2026-03', 'Applied', 'Focused on inventory and process improvement experience', 5),
                                                                                    (10, 'VoiceMetrics', 'Customer Insights Intern', '2026-03', 'Interview', 'Connected customer feedback analysis project to role', 5),
                                                                                    (11, 'CreativeStack', 'Frontend Developer Intern', '2026-02', 'Applied', 'Highlighted portfolio website redesign work', 6),
                                                                                    (12, 'MarketPulse', 'Marketing Analytics Intern', '2026-03', 'Applied', 'Linked campaign dashboard and reporting projects', 6);

INSERT INTO project_skills (project_id, skill_id) VALUES
                                                      (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
                                                      (2, 3), (2, 7), (2, 8), (2, 14),
                                                      (3, 9), (3, 14),
                                                      (4, 3), (4, 5), (4, 7), (4, 15),
                                                      (5, 6), (5, 13), (5, 3),
                                                      (6, 6), (6, 7), (6, 13), (6, 15),
                                                      (7, 1), (7, 2), (7, 3), (7, 4),
                                                      (8, 3), (8, 8), (8, 15),
                                                      (9, 1), (9, 3), (9, 14),
                                                      (10, 6), (10, 7), (10, 13),
                                                      (11, 9), (11, 10), (11, 11), (11, 12),
                                                      (12, 3), (12, 7), (12, 8), (12, 15);

INSERT INTO application_projects (application_id, project_id) VALUES
                                                                  (1, 1), (1, 7),
                                                                  (2, 2), (2, 8),
                                                                  (3, 3),
                                                                  (4, 4), (4, 9),
                                                                  (5, 5), (5, 6),
                                                                  (6, 6), (6, 12),
                                                                  (7, 1), (7, 7), (7, 11),
                                                                  (8, 8),
                                                                  (9, 4), (9, 9),
                                                                  (10, 10),
                                                                  (11, 11),
                                                                  (12, 12), (12, 4);

-- Reset the auto-increment counters to prevent primary key conflicts
ALTER TABLE portfolio_users ALTER COLUMN id RESTART WITH 7;
ALTER TABLE skills ALTER COLUMN id RESTART WITH 16;
ALTER TABLE projects ALTER COLUMN id RESTART WITH 13;
ALTER TABLE job_applications ALTER COLUMN id RESTART WITH 13;